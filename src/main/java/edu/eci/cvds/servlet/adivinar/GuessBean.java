package edu.eci.cvds.servlet.adivinar;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;


import java.util.ArrayList;
import java.util.Random;


@ManagedBean(name = "guessBean")
@ApplicationScoped
//@SessionScoped
public class GuessBean {
	private int numberWin;
    private int numIntentos = 1;
    private boolean gameState;
    private int premio = 100000;
    private ArrayList<IntentoAdivinar> intentos;
    private String error = "";

    public GuessBean() {
        error = "Bienvenido";
        intentos = new ArrayList<>();
        loadNumber();
    }

    public void loadNumber(){
        Random r = new Random();
        numberWin = r.nextInt(10);
    }

    public String getError() {
        return error;
    }

    public int getNumberGuessing() {
        return numberWin;
    }

    public void setNumberGuessing(int numberGuessing) {
        this.numberWin = numberGuessing;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void intento(String number){
        try {
            int numero = Integer.parseInt(number);
            if (!gameState) {
            	intentos.add(new IntentoAdivinar(numIntentos,numero));
            	numIntentos++;
                checkState(numero);
            }
            
        }
        catch (Exception e){
            error = e.getMessage();
        }
    }

    public ArrayList<IntentoAdivinar> getIntentos() {
        return intentos;
    }

    public void setIntentos(ArrayList<IntentoAdivinar> intentos) {
        this.intentos = intentos;
    }

    public void restart() {
        loadNumber();
        numIntentos = 1;
        intentos.clear();
        premio = 100000;
        gameState = false;
    }

    public void checkState(int check) throws GuessBeanException{
        if(numberWin == check){
            gameState = true;
            throw new GuessBeanException(GuessBeanException.winningGame);
        }
        else{
            premio -= 10000;
            if(premio <= 0){
                numIntentos = 10;
                premio = 0;
                gameState = true;
                error = "";
                throw new GuessBeanException(GuessBeanException.losingGame);
            }
        }
    }

    public int getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        this.numIntentos = numIntentos;
    }

    public boolean isGameState() {
        return gameState;
    }

    public void setGameState(boolean gameState) {
        this.gameState = gameState;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
    }

    public void CalculadoraBean (String number) {
        try {
            Integer.parseInt(number);
        }
        catch(Exception e) {
            //restart();
        }
    }

}