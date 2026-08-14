package org.gokul;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.json.McpJsonMapper;
import io.modelcontextprotocol.json.jackson2.JacksonMcpJsonMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.awt.SystemColor.text;

public class HelloMcpServer {

    private static final Logger log = LoggerFactory.getLogger(HelloMcpServer.class);

    public static void main(String[] args) throws InterruptedException {

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

        McpServerFeatures.SyncToolSpecification echoSpec = McpServerFeatures.SyncToolSpecification.builder()
                .tool(echoTool)
                .callHandler((exchange, request) -> {
                    Object raw = request.arguments().get("message");

                    if (!(raw instanceof String text) || text.isBlank()) {
                        return CallToolResult.builder()
                                .isError(true)
                                .addTextContent("'message must be a non blank string'")
                                .build();
                    }
                    log.debug("echo called: {}", text);
                    return CallToolResult.builder()
                            .addTextContent("Echo: " + text)
                            .build();
                })
                .build();

        McpServer.sync(transportProvider)
                .serverInfo("my-first-server", "1.0.0")
                .capabilities(McpSchema.ServerCapabilities.builder().tools(true).build())
                .tools(echoSpec)
                .build();

        log.info("hello-mcp-server started (stdio); awaiting message on stdin");
        Thread.currentThread().join();
    }
}