package ar.edu.itba.pod;

import java.io.Serializable;

/**
 * Resultado de la eleccion.
 */
public enum ElectionResult implements Serializable {
    WIN, LOOSE, DRAW;


    private final static long serialUID = 1982737461829123822L;
}