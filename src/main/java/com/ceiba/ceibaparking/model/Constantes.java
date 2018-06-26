package com.ceiba.ceibaparking.model;

import java.util.Calendar;

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
    public static final long FACTORMILISEG2HORAS = 3600000;
    public static final String ENDPOINT = "https://www.superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService";
    
    private Constantes(){
    	throw new IllegalStateException("Utility class");
    }
   
    public static FechaCalendario getFechaActual() {
    	Calendar calendar = Calendar.getInstance();
    	int year=calendar.get(Calendar.YEAR);
    	int mes=calendar.get(Calendar.MONTH);
    	int diaMes=calendar.get(Calendar.DAY_OF_MONTH);
    	int horaDia=calendar.get(Calendar.HOUR_OF_DAY);
    	int minuto=calendar.get(Calendar.MINUTE);
    	int second=calendar.get(Calendar.SECOND);
    	return new FechaCalendario(year,mes,diaMes,horaDia,minuto,second);
    }

}
