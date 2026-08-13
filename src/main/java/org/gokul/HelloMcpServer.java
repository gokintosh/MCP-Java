package org.gokul;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.jackson2.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloMcpServer {

    private static final Logger log = LoggerFactory.getLogger(HelloMcpServer.class);

    public static void main(String[] args) {
        System.out.println("MCP SDK available: "+ McpServer.class.getName());
    }
}
