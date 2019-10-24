package Model;

import static org.junit.jupiter.api.Assertions.*;


import com.webcheckers.Model.Piece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model tier")
public class PieceTest {

    private static final String W = "W";
    private static final String R = "RED";

    @Test
    public void ctor_testW(){
        final Piece CuT = new Piece(W);
        assertSame("W" , CuT.toString());
    }

    @Test
    public void ctor_testR(){
        final Piece Cut = new Piece(R);
        assertSame("R" , Cut.toString());

    }



}

