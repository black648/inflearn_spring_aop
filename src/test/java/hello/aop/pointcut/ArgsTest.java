package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * args
 * args : 인자가 주어진 타입의 인스턴스인 조인 포인트로 매칭
 * 기본 문법은 execution 의 args 부분과 같다.
 * execution과 args의 차이점
 * execution 은 파라미터 타입이 정확하게 매칭되어야 한다. execution 은 클래스에 선언된 정보를
 * 기반으로 판단한다.
 * args 는 부모 타입을 허용한다. args 는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다.
 */
public class ArgsTest {

    Method method;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        method = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    void args() {
        //hello(String)과 매칭
        assertThat(pointcut("args(String)").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args()").matches(method, MemberServiceImpl.class)).isFalse();
        assertThat(pointcut("args(..)").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(*)").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(String,..)").matches(method, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsVsExecution() {
        //Args
        assertThat(pointcut("args(String)").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(java.io.Serializable)").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)").matches(method, MemberServiceImpl.class)).isTrue();

        //Execution
        assertThat(pointcut("execution(* *(String))").matches(method, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("execution(* *(java.io.Serializable))").matches(method, MemberServiceImpl.class)).isFalse();
        assertThat(pointcut("execution(* *(Object))").matches(method, MemberServiceImpl.class)).isFalse();
    }



}
