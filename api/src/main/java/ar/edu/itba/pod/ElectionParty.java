package ar.edu.itba.pod;

import java.io.Serializable;

/**
 * Partidos de la elección
 */
public enum ElectionParty implements Serializable {

	LEFT_PARTY("L"),
	CENTRAL_PARTY("C"),
	RIGHT_PARTY("R");

	private final static long serialUID = 1293819283123222315L;
	
	private String code;

	ElectionParty(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
}