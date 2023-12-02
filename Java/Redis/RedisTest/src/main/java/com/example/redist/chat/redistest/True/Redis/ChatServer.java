package com.example.redist.chat.redistest.True.Redis;

import com.example.redist.chat.redistest.True.Redis.handler.ChatServerHandler;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Log4j2
public class ChatServer {

    public static void main(String[] args) throws Exception {
        new ChatServer(6001).run();
    }

    private final int port;
    //추가된 전역변수들
    private final JedisPool jedisPool;

    //재정의된 생성자
    public ChatServer(int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);
        config.setMaxIdle(64);
        config.setMinIdle(16);
        config.setTestOnBorrow(true);

        this.port = port;
        //이부분에 주의하세요. Docker에서 레디스에 할당한 포트 번호가 필요합니다.
        this.jedisPool = new JedisPool(config, "localhost", port, 10000);
    }

    public void run() throws Exception {

        try (
                CloseableEventLoopGroup bossGroup = new CloseableEventLoopGroup(new NioEventLoopGroup());
                CloseableEventLoopGroup workerGroup = new CloseableEventLoopGroup(new NioEventLoopGroup());
        ) {
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
                                    new ChatServerHandler(jedisPool)
                            );
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("해당 포트에서 서버 시작됨 : {} ", port);
            future.channel().closeFuture().sync();
        }
    }

    @RequiredArgsConstructor @Getter
    protected static class CloseableEventLoopGroup implements AutoCloseable {
        private final EventLoopGroup group;
        @Override
        public void close() {
            group.shutdownGracefully();
        }
    }
}
