package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayersTest {

    Players brianYang;
    Players lohKeanYew;
    Players kentoMomota;
    Players reyLuo;

    Players zhengSiWeiAndHuangYaQiong;
    Players rankireddyAndShetty;

    @BeforeEach
    public void setup() {
        brianYang = new Players("Brian Yang");
        lohKeanYew = new Players("Loh Kean Yew");
        kentoMomota = new Players("Kento Momota");
        reyLuo = new Players("Wei Luo");

        zhengSiWeiAndHuangYaQiong = new Players("Zheng Si Wei", "Huang Ya Qiong");
        rankireddyAndShetty = new Players("Satwiksairaj Rankireddy", "Chirag Shetty");
    }

    @Test
    public void setAndGetNameTest() {
        assertEquals("Wei Luo", reyLuo.getName());
        reyLuo.setPlayer1("Rey Luo");
        assertEquals("Rey Luo", reyLuo.getName());

        assertEquals("Zheng Si Wei and Huang Ya Qiong", zhengSiWeiAndHuangYaQiong.getName());
        zhengSiWeiAndHuangYaQiong.setPlayer2("Huang Dong Ping");
        assertEquals("Zheng Si Wei and Huang Dong Ping", zhengSiWeiAndHuangYaQiong.getName());

        kentoMomota.setPlayer2("Loh Kean Yew");
        assertEquals("Kento Momota", kentoMomota.getName());
    }

    @Test
    public void setAndGetNationalityTest() {
        assertNull(brianYang.getNationality());
        brianYang.setNationality("Canadian");
        assertEquals("Canadian", brianYang.getNationality());

        assertNull(kentoMomota.getNationality());
        kentoMomota.setNationality("Japanese");
        assertEquals("Japanese", kentoMomota.getNationality());

        assertNull(rankireddyAndShetty.getNationality());
        rankireddyAndShetty.setNationality("Indian");
        assertEquals("Indian", rankireddyAndShetty.getNationality());
    }

    @Test
    public void isPairTest() {
        assertFalse(lohKeanYew.isPair());
        assertFalse(kentoMomota.isPair());
        assertTrue(zhengSiWeiAndHuangYaQiong.isPair());
        assertTrue(rankireddyAndShetty.isPair());
    }
}
