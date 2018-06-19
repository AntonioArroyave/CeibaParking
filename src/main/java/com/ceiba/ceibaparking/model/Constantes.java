package com.ceiba.ceibaparking.model;

public class Constantes {
	public static final int CILINDRAJEMOTO = 500;
	public static final int AUMENTOCILINDRAJE = 2000;
	public static final int HORASMINIMASDELDIA = 9;
	public static final int VALORHORACARRO=1000;
    public static final int LIMITECARROS=20;
    public static final int LIMITEMOTOS=10;
    public static final int VALORHORAMOTO=500;
    public static final int VALORDIACARRO=8000;
    public static final int VALORDIAMOTO=4000;
    public static final String 	VALIDACIONLETRA = "A";
    public static final int HORASDELDIA = 24;
    public static final int FACTORMILISEG2HORAS = 3600000;
    
    private Constantes(){
    	throw new IllegalStateException("Utility class");
    }
}
