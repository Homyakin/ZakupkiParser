package ru.homyakin.zakupki.database.purchase_protocol.utils;

import ru.homyakin.zakupki.models._223fz.types.AcceptedType;
import ru.homyakin.zakupki.models._223fz.types.AcceptedType2;
import ru.homyakin.zakupki.models._223fz.types.WinnerIndication;
import ru.homyakin.zakupki.models._223fz.types.WinnerIndication3;

public class CommonMapper {
    public static AcceptedType2 mapAcceptedTypeToAcceptedType2(AcceptedType acceptedType) {
        return switch (acceptedType) {
            case F -> AcceptedType2.F;
            case T -> AcceptedType2.T;
        };
    }

    public static WinnerIndication mapWinnerIndication3ToWinnerIndication(WinnerIndication3 winnerIndication3) {
        return switch (winnerIndication3) {
            case F, W -> WinnerIndication.F;
            case S -> WinnerIndication.S;
        };
    }
}
