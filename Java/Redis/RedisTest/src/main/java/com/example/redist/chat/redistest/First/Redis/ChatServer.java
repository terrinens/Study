package com.example.redist.chat.redistest.First.Redis;

import com.example.redist.chat.redistest.First.Redis.handler.ChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class ChatServer {

    private final int port;

    public void run() throws Exception {

        try (
                // 이너 클래스 CloseableEventLoopGroup 참조 하세요
                CloseableEventLoopGroup bossGroup = new CloseableEventLoopGroup(new NioEventLoopGroup());
                CloseableEventLoopGroup workerGroup = new CloseableEventLoopGroup(new NioEventLoopGroup());
        ) {
            // 서버 부트스트랩을 생성합니다. 이 부트스트랩은 css 프레임워크가 아닌 다른겁니다.
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup.getGroup(), workerGroup.getGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    new StringDecoder(),
                                    new StringEncoder(),
                                    new ChatServerHandler()
                            );
                        }
                    });

            // 채널 즉 접속 포트를 바인드하고 싱크를 맞춰줍니다.
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("해당 포트에서 서버 시작됨 : {} ", port);
            // 싱크로 포트를 맞췄으면 더 이상 자원이 필요하지 않으므로 종료 시켜줍니다.
            future.channel().closeFuture().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(6001).run();
    }

    /**
     * try 단계에서 리소스를 선언하고 끝나는 시점에서 자동적으로 리소스 해제를 위한 메서드 <br>
     * try-with-resources 기법
     */
    protected static class CloseableEventLoopGroup implements AutoCloseable {
        private final EventLoopGroup group;
        CloseableEventLoopGroup(EventLoopGroup group) {this.group = group;}
        EventLoopGroup getGroup() {return this.group;}
        @Override
        public void close() {group.shutdownGracefully();}
    }
}
