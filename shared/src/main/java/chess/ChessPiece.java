package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor color;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return color == piece.color && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING, // 1 square in all directions; cannot move into check
        QUEEN, // any # of squares in all directions
        BISHOP, // any # of squares diagonally
        KNIGHT, // (horse); 2-1 movement; complicated?
        ROOK, // any # of squares vertically/horizontally
        PAWN // 1 square forward (exception: on first turn, it can move 2 forward); kills diagonally; can become a queen?
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
        //throw new RuntimeException("Not implemented");
    }

    private Collection<ChessMove> findLinearMoves(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ArrayList<ChessMove> possibleMoves = new ArrayList<>();

        // loop through all squares in a straight line
        for (int i = 1; i < 8; i++) {
            int nextRow = row + i * rowDirection;
            int nextCol = col + i * colDirection;

            // check if the position is valid
            if (inValid(nextRow, nextCol)) {
                break;
            }

            // position is valid, make ChessPosition class at location
            ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);

            // check if there's a piece on the next square
            ChessPiece piece = board.getPiece(nextPosition);

            if (piece == null) { // new square is empty, add move to possibleMoves list
                possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
            } else { // new square is occupied by an...
                if (piece.getTeamColor() != this.getTeamColor()) {  // ...opponent, add move to list
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
                    break;
                } else { // ...ally, do not add to list
                    break;
                }
            }
        }
        return possibleMoves;
    }

    private ChessMove findMove(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int nextRow = row + rowDirection;
        int nextCol = col + colDirection;

        // check if the position is valid
        if (inValid(nextRow, nextCol)) {
            return null;
        }

        // position is valid, make ChessPosition class at location
        ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
        // check if there's already a piece there
        ChessPiece piece = board.getPiece(nextPosition);

        if (piece == null) { // new square is empty, add move to possibleMoves list
            return new ChessMove(myPosition, nextPosition, null);
        } else { // new square is occupied by an...
            if (piece.getTeamColor() != this.getTeamColor()) {  // ...opponent, add move to list
                return new ChessMove(myPosition, nextPosition, null);
            } else { // ...ally, do not add to list
                return null;
            }
        }
    }

    private Collection<ChessMove> findPawnMoves(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        ArrayList<ChessMove> possibleMoves = new ArrayList<>();

        int nextRow = row + rowDirection;
        int nextCol = col + colDirection;

        // check if the position is valid
        if (inValid(nextRow, nextCol)) {
            return new ArrayList<>(); // returns an empty list, should get filtered out by the .addAll call
        }

        // position is valid, make ChessPosition class at location
        ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
        // check if there's already a piece there
        ChessPiece piece = board.getPiece(nextPosition);

        if (colDirection != 0) { // diagonal square
            if (piece != null) { // new square is occupied, add to list
                if (promotePawn(nextPosition)){
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null)); // PLACEHOLDER!! promotion not yet implemented
                }else possibleMoves.add(new ChessMove(myPosition, nextPosition, null));
            }
        }

        if (colDirection == 0) { // forward square
            if (piece == null) { // new square is empty, add to list
                if (promotePawn(nextPosition)){
                    possibleMoves.add(new ChessMove(myPosition, nextPosition, null)); // PLACEHOLDER!! promotion not yet implemented
                }else possibleMoves.add(new ChessMove(myPosition, nextPosition, null));

                if (firstMove(myPosition)) {  // first move check
                    ChessPosition extraPosition = null;
                    if (this.color == ChessGame.TeamColor.WHITE) { extraPosition = new ChessPosition(row + 2, col);}
                    if (this.color == ChessGame.TeamColor.BLACK) { extraPosition = new ChessPosition(row - 2, col);}

                    // check if there's already a piece there
                    ChessPiece extraPiece = board.getPiece(extraPosition);

                    if (extraPiece == null){ // extra square is empty, add to list
                        possibleMoves.add(new ChessMove(myPosition, extraPosition, null));
                    }
                }
            }
        }
        return possibleMoves;
    }

    private boolean inValid(int nextRow, int nextCol) {
        return nextRow <= 0 || nextRow > 8 || nextCol <= 0 || nextCol > 8;
    }

    private boolean firstMove(ChessPosition myPosition) {
        int row = myPosition.getRow();
        boolean isFirst = false;
        ChessGame.TeamColor color = getTeamColor();

        // check color and position
        if (color == ChessGame.TeamColor.WHITE && row == 2) {
            isFirst = true;
        } else if (color == ChessGame.TeamColor.BLACK && row == 7) {
            isFirst = true;
        }

        return isFirst;
    }

    private boolean promotePawn (ChessPosition possiblePosition){
        int row = possiblePosition.getRow();
        return row == 1 || row == 8;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        Collection<ChessMove> validMoves = new ArrayList<>();

        switch (this.type) {
            case KING -> {
                int[][] directions = {
                        {1, 0}, // North
                        {1, 1},   // North East
                        {0, 1}, // East
                        {-1, 1},  // South East
                        {-1, 0}, // South
                        {-1, -1}, // South West
                        {0, -1},  // West
                        {1, -1}   // North West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    ChessMove possibleMove = findMove(board, myPosition, direction[0], direction[1]);
                    if (possibleMove != null) {
                        validMoves.add(possibleMove);
                    }
                }
            }
            case QUEEN -> {
                int[][] directions = {
                        {1, 0}, // North
                        {1, 1},   // North East
                        {0, 1}, // East
                        {-1, 1},  // South East
                        {-1, 0}, // South
                        {-1, -1}, // South West
                        {0, -1},  // West
                        {1, -1}   // North West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    validMoves.addAll(findLinearMoves(board, myPosition, direction[0], direction[1]));
                }
            }
            case BISHOP -> {
                int[][] directions = {
                        {1, 1},   // North East
                        {-1, 1},  // South East
                        {-1, -1}, // South West
                        {1, -1}   // North West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    validMoves.addAll(findLinearMoves(board, myPosition, direction[0], direction[1]));
                }
            }
            case KNIGHT -> {
                int[][] directions = {
                        {2, 1},
                        {1, 2},
                        {-1, 2},
                        {-2, 1},
                        {-2, -1},
                        {-1, -2},
                        {1, -2},
                        {2, -1}
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    ChessMove possibleMove = findMove(board, myPosition, direction[0], direction[1]);
                    if (possibleMove != null) {
                        validMoves.add(possibleMove);
                    }
                }

            }
            case ROOK -> {
                int[][] directions = {
                        {1, 0}, // North
                        {0, 1}, // East
                        {-1, 0}, // South
                        {0, -1},  // West
                };

                // loop through all possible directions
                for (int[] direction : directions) {
                    validMoves.addAll(findLinearMoves(board, myPosition, direction[0], direction[1]));
                }
            }
            case PAWN -> {
                switch (this.color) {
                    case WHITE -> {
                        int[][] directions = {
                                {1, -1}, // North-West
                                {1, 0}, // North
                                {1, 1}, // North-East
                        };
                        for (int[] direction : directions) validMoves.addAll(findPawnMoves(board, myPosition, direction[0], direction[1]));
                    }

                    case BLACK -> {
                        int[][] directions = {
                                {-1, -1}, // North-West
                                {-1, 0}, // North
                                {-1, 1}, // North-East
                        };
                        for (int[] direction : directions) validMoves.addAll(findPawnMoves(board, myPosition, direction[0], direction[1]));
                    }
                }
            }
        }
        return validMoves;
        //throw new RuntimeException("Not implemented");
    }


}
