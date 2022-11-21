package com.example.fecafootdemo.utils;

public interface LooperBuffer {
	void add(byte[] buffer);

	byte[] getFullPacket();
}