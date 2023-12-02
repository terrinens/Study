package com.example.redist.chat.redistest.True.Redis.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Log4j2 @RequiredArgsConstructor //추가된 기본 생성자 어노테이션
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    //추가된 전역 변수
    private final JedisPool jedisPool;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        log.info("서버 채널 : {}", message);
        
        try (CloseableJedis closeableJedis = new CloseableJedis(this.jedisPool.getResource())) {
            /*
            가독성을 위해 Jedis 객체에 주입합니다.
            그렇지 않으면 closeableJedis.jedis.keys() 방법으로 사용해도 됩니다.
            결국 취향차이죠.
            */
            Jedis jedis = closeableJedis.getJedis();
            
            //레디스 서버에 있는 모든 키를 가져오기
            Set<String> channelKeys = jedis.keys("*");

            //각 채널에 메세지를 전달합니다.
            for (String channelKey : channelKeys) {
                jedis.publish(channelKey, writerAndMessage(ctx, message));
            }
        }

        //각 채널의 채팅 로그를 기록 하기 위한 메서드
        try (CloseableJedis closeableJedis = new CloseableJedis(this.jedisPool.getResource())) {

            String key = "chat_history";
            String value = writerAndMessage(ctx, message) + "\n";

            closeableJedis.jedis.append(key, value);
        } catch (Exception e) {
            log.error("채팅 이력 저장 실패 : ", e);
        }

        ctx.channel().parent().writeAndFlush(writerAndMessage(ctx, message) + "\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage(), cause);
    }

    //추가된 리소스 관리 메서드
    /**Jedis 리소스 관리 메서드*/
    @Getter
    @Setter
    protected static class CloseableJedis implements AutoCloseable {
        private Jedis jedis;
        CloseableJedis(Jedis jedis) {
            this.jedis = jedis;
        }
        @Override
        public void close() {
            jedis.close();
        }
    }

    // 추가된 메서드
    /**메시지 생성 규칙에 따른 메세지 생성 메서드*/
    protected static String writerAndMessage (ChannelHandlerContext ctx, String message) {
        return "(" + ctx.channel().remoteAddress() + ") : " + message;
    }
}
