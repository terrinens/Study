package com.example.redist.chat.redistest.First.Redis;


import com.example.redist.chat.redistest.First.Redis.handler.ChatClientHandler;
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
                // ChatServer에 이너 클래스를 확인하세요. 자동으로 자원 해제 합니다.
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

            // 챗팅 서버 클래스에서 할당된 포트 번호를 동일하게 호스트와 싱크를 맞추기위한 변수입니다.
            Channel channel = bootstrap.connect(host, port).sync().channel();

            /*
            사용자가 입력한 채팅을 읽어 들이기 위한 변수입니다.
            BufferedReader는 Enter를 줄바꿈으로 인식하는 메서드 입니다.
            그러므로 Enter를 눌러 채팅 할 경우에 한줄로 인식하게 되어 보내집니다.

            ex : 진짜 (Enter) 하기 싫다 (Enter) 를 입력하게 되면은 실제로 보내지는건
            📌 [진짜] 📌
            📌 [하기 싫다] 📌 로 보내집니다.
            */
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            /*
            사용자가 입력한 채팅을 계속해서 서버로 보내기 위한 무한루프 평가식입니다.
             */
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    // 해당 블럭은 사용자가 아무것도 입력 하지 않았을 경우에 오류를 방지하기 위한 블럭입니다.
                    // 혹은 더 이상 보낼 메세지가 없거나요.
                    break;
                }

                /*
                실제로 집행 하는 코드입니다. 그 이전이 가상화 단계인거고 이 코드는 실제로 보내는 코드인거죠.
                여기서 의문이 들겠죠? 왜 \r\n 을 사용해서 TCP 형식으로 명시했냐구요?
                그 이유는 BufferedReader 라는 메서드가 줄바꿈을 없애버리고 보내기 때문에 확실히 명시하기 위해서랍니다.
                Flush 단계에서 \r\n이 존재해야지만 하나의 메세지로 인식하는데 그게 없으면 그저 오류를 반환 할 뿐이죠.
                */
                channel.writeAndFlush(line + "\r\n");
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new ChatClient("localhost", 6001).run();
    }
}
