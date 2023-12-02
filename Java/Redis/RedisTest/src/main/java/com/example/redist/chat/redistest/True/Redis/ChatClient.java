package com.example.redist.chat.redistest.True.Redis;


import com.example.redist.chat.redistest.True.Redis.handler.ChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
@Log4j2
public class ChatClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        new ChatClient("localhost", 6001).run();
    }

    private final String host;
    private final int port;

    /**
     * 주의! ChatServer와 포트 번호가 동일해야합니다!!!
     * 서버 포트를 이용해 쌍방향 통신이므로 무조건 포트 번호가 같아야 합니다!
     * 이거 때문에 시간 엄청 잡아 먹었네요
     */
    public void run() throws InterruptedException, IOException {
        log.info("포트 번호는? {}", port);

        try (
                ChatServer.CloseableEventLoopGroup loopGroup =
                        new ChatServer.CloseableEventLoopGroup(new NioEventLoopGroup())
        ) {
            Bootstrap bootstrap = new Bootstrap()
                    .group(loopGroup.getGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    new StringDecoder(),
                                    new StringEncoder(),
                                    new ChatClientHandler()
                            );
                        }
                    });

            Channel channel = bootstrap.connect(host, port).sync().channel();

            Thread thread = new Thread(() -> {
                BufferedReader bufferedReader = null;
                try {
                    log.info("트라이 진입");
                    bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                    while (true) {
                        log.info("입력 받기 시작");
                        String input = bufferedReader.readLine();
                        log.info("입력 받기 끝");
                        if (input == null || "quit".equalsIgnoreCase(input)) {
                            break;
                        }
                        channel.writeAndFlush(input + "\r\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            log.error("종료 오류 ", e);
                        }
                    }
                }
            });

            thread.start();
        }
    }
}
