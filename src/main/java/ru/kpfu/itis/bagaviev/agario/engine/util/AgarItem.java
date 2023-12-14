package ru.kpfu.itis.bagaviev.agario.engine.util;

import ru.kpfu.itis.bagaviev.agario.engine.objects.Agar;

public class AgarItem {
    private Integer id;
    private String nickname;
    private Agar agar;

    public AgarItem(Integer id, String nickname, Agar agar) {
        this.id = id;
        this.nickname = nickname;
        this.agar = agar;
    }

    public Integer getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Agar getAgar() {
        return agar;
    }

}
