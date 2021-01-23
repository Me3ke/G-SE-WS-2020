package de.techfak.se.multiplayer.server.response_body;

import de.techfak.se.multiplayer.game.Board;

/**
 * The response containing a board configuration.
 */
public class BoardResponse extends ResponseObject {

    private String board;

    public BoardResponse() {
        super();
    }

    public BoardResponse(final Board board) {
        super(true, "Die Anfrage war erfolgreich.");
        this.board = board.getBoardString();
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(final String board) {
        this.board = board;
    }
}
