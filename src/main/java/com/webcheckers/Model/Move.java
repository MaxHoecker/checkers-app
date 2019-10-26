package com.webcheckers.Model;

public class Move {
    Position start;
    Position end;

    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }

    public String toString(){
        return "start:" + start + "\nend:" + end;
    }
}
