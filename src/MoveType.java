public enum MoveType {

    Kill, //standard kill - killed a non-king piece.
    Standard, //non-king piece move to an empty diagonal.
    Illegal, //invalid moves.
    KingStandard, //basic move of king to empty diagonal
    KingIllegal, //invalid king move
    CrownKing, //move where a non-king piece becomes king (reaching the other side of the board)
    CrownKingKill, //a non-king piece kills another non-king piece and reaches the other side of the board.
    CrownKingSlay, //a non-king piece kills another king piece and reaches the other side of the board.
    KingSlay, //a piece kills a king piece
}
