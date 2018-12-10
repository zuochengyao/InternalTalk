package com.megvii.blockchain;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class ServerInitListener extends ContextLoaderListener
{
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        super.contextInitialized(event);
        BlockChain.getInstance().init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        super.contextDestroyed(event);
    }
}
