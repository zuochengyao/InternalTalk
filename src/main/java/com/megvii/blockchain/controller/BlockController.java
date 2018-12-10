package com.megvii.blockchain.controller;

import com.megvii.blockchain.entity.Transaction;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/block")
public class BlockController
{
    List<Transaction> transactions = new ArrayList<>();

    @RequestMapping(method = RequestMethod.POST, value = "/transaction", consumes = "application/json")
    @ResponseBody
    public String transaction(HttpServletRequest request)
    {
        // curl "localhost:8080/block/transaction" -H "Content-Type: application/json" -d '{"from": "fasdc", "to":"asgeqwer", "amount": 3}'
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
        return String.format(Locale.CHINESE, "New transaction:\r\nFrom:%s\r\nTo:%s\r\nAmount:%s\r\nTransaction submission successful\r\n", json.opt("from"), json.opt("to"), json.opt("amount"));
    }
}
