package ru.homyakin.zakupki.database;

import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.models._223fz.types.OkdpProductType;
import ru.homyakin.zakupki.models._223fz.types.OkeiProductType;
import ru.homyakin.zakupki.models._223fz.types.Okpd2ProductType;
import ru.homyakin.zakupki.models._223fz.types.OkpdProductType;
import ru.homyakin.zakupki.models._223fz.types.Okved2ProductType;
import ru.homyakin.zakupki.models._223fz.types.OkvedProductType;

@Service
public class ClassifierService {
    public String getClassifierCode(OkpdProductType okpd) {
        if (okpd == null) return null;
        else return okpd.getCode();
    }

    public String getClassifierName(OkpdProductType okpd) {
        if (okpd == null) return null;
        else return okpd.getName();
    }

    public String getClassifierCode(OkdpProductType okdp) {
        if (okdp == null) return null;
        else return okdp.getCode();
    }

    public String getClassifierName(OkdpProductType okdp) {
        if (okdp == null) return null;
        else return okdp.getName();
    }

    public String getClassifierCode(Okpd2ProductType okdp2) {
        if (okdp2 == null) return null;
        else return okdp2.getCode();
    }

    public String getClassifierName(Okpd2ProductType okdp2) {
        if (okdp2 == null) return null;
        else return okdp2.getName();
    }

    public String getClassifierCode(OkvedProductType okved) {
        if (okved == null) return null;
        else return okved.getCode();
    }

    public String getClassifierName(OkvedProductType okved) {
        if (okved == null) return null;
        else return okved.getName();
    }

    public String getClassifierCode(Okved2ProductType okved2) {
        if (okved2 == null) return null;
        else return okved2.getCode();
    }

    public String getClassifierName(Okved2ProductType okved2) {
        if (okved2 == null) return null;
        else return okved2.getName();
    }

    public String getClassifierCode(OkeiProductType okei) {
        if (okei == null) return null;
        else return okei.getCode();
    }

    public String getClassifierName(OkeiProductType okei) {
        if (okei == null) return null;
        else return okei.getName();
    }
}
