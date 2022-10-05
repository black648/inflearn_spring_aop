package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberService = (MemberService) proxyFactory.getProxy();

        log.info("proxy class = {}", memberService.getClass());

        //JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패.. ClassCastException 발생
        assertThrows(ClassCastException.class, () -> {MemberServiceImpl service = (MemberServiceImpl) memberService;});
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //CGLIB 프록시

        MemberService memberService = (MemberService) proxyFactory.getProxy();

        log.info("proxy class = {}", memberService.getClass());

        //JDK 동적 프록시를 구현 클래스로 캐스팅 시도 성공
        assertThrows(ClassCastException.class, () -> {MemberServiceImpl service = (MemberServiceImpl) memberService;});
    }

}
