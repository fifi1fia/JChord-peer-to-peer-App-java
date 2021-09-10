/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jchord;


public class Finger {

	ChordKey start;

	ChordNode node;

	public Finger(ChordKey start, ChordNode node) {
		this.node = node;
		this.start = start;
	}

	public ChordKey getStart() {
		return start;
	}

	public void setStart(ChordKey start) {
		this.start = start;
	}

	public ChordNode getNode() {
		return node;
	}

	public void setNode(ChordNode node) {
		this.node = node;
	}

}
