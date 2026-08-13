package org.gokul;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.jackson2.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloMcpServer {

    private static final Logger log = LoggerFactory.getLogger(HelloMcpServer.class);

    public static void main(String[] args) {

        System.out.println("hello");
        McpJsonMapper jsonMapper = new JacksonMcpJsonMapper(new ObjectMapper());
        var transportProvider = new StdioServerTransportProvider(jsonMapper);

        Tool echoTool = Tool.builder("echo", jsonMapper, """
                        {
                          "type": "object",
                          "properties": {
                            "message": {
                              "type": "string",
                              "description": "The text to echo back"
                            }
                          },
                          "required": ["message"]
                        }
                        """)
                .description("Returns whatever text you pass in. Useful for testing.")
                .build();
    }
}