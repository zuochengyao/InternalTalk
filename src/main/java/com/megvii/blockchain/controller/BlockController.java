package com.megvii.blockchain.controller;

import com.megvii.blockchain.BlockChain;
import com.megvii.blockchain.entity.Block;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/block")
public class BlockController
{
    @RequestMapping(method = RequestMethod.POST, value = "generate_block")
    @ResponseBody
    public String generateBlock(@RequestParam int size, HttpServletRequest request)
    {
        if (size > 0)
        {
            BlockChain.getInstance().getNextBlock(request.getRemoteHost(), request.getRemotePort());
            generateBlock(--size, request);
        }
        return "Success";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transaction", consumes = "application/json")
    @ResponseBody
    public String transaction(HttpServletRequest request)
    {
        // curl "localhost:8080/block/transaction.action" -H "Content-Type:application/json" -d '{"from": "A", "to":"B", "amount": 3}'
        StringBuilder data = new StringBuilder();
        String line;
        BufferedReader reader;
        try
        {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject(data.toString());
        String from = json.optString("from");
        String to = json.optString("to");
        int amount = json.optInt("amount");
        BlockChain.getInstance().newTransaction(from, to, amount);
        return String.format(Locale.CHINESE, "New transaction:\r\nFrom:%s\r\nTo:%s\r\nAmount:%d\r\nTransaction submission successful\r\n", from, to, amount);
    }

    // curl "localhost:8080/block/pow.action" -H "Content-Type:application/x-www-form-urlencoded" -d "{}"
    @RequestMapping(method = RequestMethod.POST, value = "/pow")
    @ResponseBody
    public String pow(HttpServletRequest request)
    {
        String host = request.getRemoteHost();
        int port = request.getRemotePort();

        Block block = BlockChain.getInstance().getNextBlockByPOW(host, port);
        return block.hash();
    }

}
