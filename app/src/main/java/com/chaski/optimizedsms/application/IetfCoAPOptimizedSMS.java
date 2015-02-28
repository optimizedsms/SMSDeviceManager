package com.chaski.optimizedsms.application;
/**
 *
 * IeftCoAPOptimizedSMS.java
 * Created by sam and mike on 1/12/15.
 *
 * The heart of the application.
 * Translates SMS messages into optimized SMS messages and back to text.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) Chaski Telecommunications, Inc.
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.ByteBuffer;


public class IetfCoAPOptimizedSMS {

    public static byte testmessage1[] = {(byte) 'b', (byte) 'E', (byte) 'B',
            (byte) 0x89, (byte) 0x11, (byte) '(', (byte) 0xa7, (byte) 0x73,
            (byte) 0x6f, (byte) 0x6d, (byte) 0x65, (byte) 0x74, (byte) 0x6f,
            (byte) 0x6b, (byte) 0x3c, (byte) 0x2f, (byte) 0x3e, (byte) 0x3b,
            (byte) 0x74, (byte) 0x69, (byte) 0x74, (byte) 0x6c, (byte) 0x65,
            (byte) 0x3d, (byte) 0x22, (byte) 0x47, (byte) 0x65, (byte) 0x6e,
            (byte) 0x65, (byte) 0x72, (byte) 0x61, (byte) 0x6c, (byte) 0x20,
            (byte) 0x49, (byte) 0x6e, (byte) 0x66, (byte) 0x6f, (byte) 0x22,
            (byte) 0x3b, (byte) 0x63, (byte) 0x74, (byte) 0x3d, (byte) 0x30,
            (byte) 0x2c, (byte) 0x3c, (byte) 0x2f, (byte) 0x74, (byte) 0x69,
            (byte) 0x6d, (byte) 0x65, (byte) 0x3e, (byte) 0x3b, (byte) 0x69,
            (byte) 0x66, (byte) 0x3d, (byte) 0x22, (byte) 0x63, (byte) 0x6c,
            (byte) 0x6f, (byte) 0x63, (byte) 0x6b, (byte) 0x22, (byte) 0x3b,
            (byte) 0x72, (byte) 0x74, (byte) 0x3d, (byte) 0x22, (byte) 0x54,
            (byte) 0x69, (byte) 0x63, (byte) 0x6b, (byte) 0x73, (byte) 0x22,
            (byte) 0x3b, (byte) 0x74, (byte) 0x69, (byte) 0x74, (byte) 0x6c,
            (byte) 0x65, (byte) 0x3d, (byte) 0x22, (byte) 0x49, (byte) 0x6e,
            (byte) 0x74, (byte) 0x65, (byte) 0x72, (byte) 0x6e, (byte) 0x61,
            (byte) 0x6c, (byte) 0x20, (byte) 0x43, (byte) 0x6c, (byte) 0x6f,
            (byte) 0x63, (byte) 0x6b, (byte) 0x22, (byte) 0x3b, (byte) 0x63,
            (byte) 0x74, (byte) 0x3d, (byte) 0x30, (byte) 0x2c, (byte) 0x3c,
            (byte) 0x2f, (byte) 0x61, (byte) 0x73, (byte) 0x79, (byte) 0x6e,
            (byte) 0x63, (byte) 0x3e, (byte) 0x3b, (byte) 0x63, (byte) 0x74,
            (byte) 0x3d, (byte) 0x30,

    };

    public static byte testmessage2[] = {(byte) 0, (byte) 1, (byte) 2,
            (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8,
            (byte) 9, (byte) 10, (byte) 11, (byte) 12, (byte) 13, (byte) 14,
            (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 20,
            (byte) 21, (byte) 22, (byte) 23, (byte) 24, (byte) 25, (byte) 26,
            (byte) 27, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 32,
            (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 38,
            (byte) 39, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44,
            (byte) 45, (byte) 46, (byte) 47, (byte) 48, (byte) 49, (byte) 50,
            (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56,
            (byte) 57, (byte) 58, (byte) 59, (byte) 60, (byte) 61, (byte) 62,
            (byte) 63, (byte) 64, (byte) 65, (byte) 66, (byte) 67, (byte) 68,
            (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74,
            (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80,
            (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86,
            (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 91, (byte) 92,
            (byte) 93, (byte) 94, (byte) 95, (byte) 96, (byte) 97, (byte) 98,
            (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103,
            (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108,
            (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113,
            (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118,
            (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 123,
            (byte) 124, (byte) 125, (byte) 126, (byte) 127, (byte) 128,
            (byte) 129, (byte) 130, (byte) 131, (byte) 132, (byte) 133,
            (byte) 134, (byte) 135, (byte) 136, (byte) 137, (byte) 138,
            (byte) 139, (byte) 140, (byte) 141, (byte) 142, (byte) 143,
            (byte) 144, (byte) 145, (byte) 146, (byte) 147, (byte) 148,
            (byte) 149, (byte) 150, (byte) 151, (byte) 152, (byte) 153,
            (byte) 154, (byte) 155, (byte) 156, (byte) 157, (byte) 158,
            (byte) 159, (byte) 160, (byte) 161, (byte) 162, (byte) 163,
            (byte) 164, (byte) 165, (byte) 166, (byte) 167, (byte) 168,
            (byte) 169, (byte) 170, (byte) 171, (byte) 172, (byte) 173,
            (byte) 174, (byte) 175, (byte) 176, (byte) 177, (byte) 178,
            (byte) 179, (byte) 180, (byte) 181, (byte) 182, (byte) 183,
            (byte) 184, (byte) 185, (byte) 186, (byte) 187, (byte) 188,
            (byte) 189, (byte) 190, (byte) 191, (byte) 192, (byte) 193,
            (byte) 194, (byte) 195, (byte) 196, (byte) 197, (byte) 198,
            (byte) 199, (byte) 200, (byte) 201, (byte) 202, (byte) 203,
            (byte) 204, (byte) 205, (byte) 206, (byte) 207, (byte) 208,
            (byte) 209, (byte) 210, (byte) 211, (byte) 212, (byte) 213,
            (byte) 214, (byte) 215, (byte) 216, (byte) 217, (byte) 218,
            (byte) 219, (byte) 220, (byte) 221, (byte) 222, (byte) 223,
            (byte) 224, (byte) 225, (byte) 226, (byte) 227, (byte) 228,
            (byte) 229, (byte) 230, (byte) 231, (byte) 232, (byte) 233,
            (byte) 234, (byte) 235, (byte) 236, (byte) 237, (byte) 238,
            (byte) 239, (byte) 240, (byte) 241, (byte) 242, (byte) 243,
            (byte) 244, (byte) 245, (byte) 246, (byte) 247, (byte) 248,
            (byte) 249, (byte) 250, (byte) 251, (byte) 252, (byte) 253,
            (byte) 254, (byte) 255};


    Map<Integer, Byte> map = new HashMap<Integer, Byte>();
    Map<Byte, Integer> decodeMap = new HashMap<Byte, Integer>();

    public void loadMap() {

        map.put(100, (byte) 0x0b);
        map.put(101, (byte) 0x0c);
        map.put(102, (byte) 0x0e);
        map.put(110, (byte) 0x0f);
        map.put(111, (byte) 0x10);
        map.put(112, (byte) 0x11);
        map.put(120, (byte) 0x12);
        map.put(121, (byte) 0x13);
        map.put(122, (byte) 0x14);
        map.put(200, (byte) 0x15);
        map.put(201, (byte) 0x16);
        map.put(202, (byte) 0x17);
        map.put(210, (byte) 0x18);
        map.put(211, (byte) 0x19);
        map.put(212, (byte) 0x1a);
        map.put(220, (byte) 0x1c);
        map.put(221, (byte) 0x1d);
        map.put(222, (byte) 0x1e);

        decodeMap.put((byte) 0x0b, 0x100);
        decodeMap.put((byte) 0x0c, 0x101);
        decodeMap.put((byte) 0x0e, 0x102);
        decodeMap.put((byte) 0x0f, 0x110);
        decodeMap.put((byte) 0x10, 0x111);
        decodeMap.put((byte) 0x11, 0x112);
        decodeMap.put((byte) 0x12, 0x120);
        decodeMap.put((byte) 0x13, 0x121);
        decodeMap.put((byte) 0x14, 0x122);
        decodeMap.put((byte) 0x15, 0x200);
        decodeMap.put((byte) 0x16, 0x201);
        decodeMap.put((byte) 0x17, 0x202);
        decodeMap.put((byte) 0x18, 0x210);
        decodeMap.put((byte) 0x19, 0x211);
        decodeMap.put((byte) 0x1a, 0x212);
        decodeMap.put((byte) 0x1c, 0x220);
        decodeMap.put((byte) 0x1d, 0x221);
        decodeMap.put((byte) 0x1e, 0x222);

    }

    public SMSbyteacter translate(int jeb) {
        SMSbyteacter retVal = new SMSbyteacter();
        // 8 bytes characters
        // 0x00 to 0x07 are represented as 0x01 to 0x08
        if (jeb >= 0 && jeb <= 7) {
            retVal.byteacter = (byte) (jeb + 1);
            retVal.prefix = 0;
            return retVal;
        }


        // This leaves 0x08 to 0x1F and 0x80 to 0xFF.
        // Of these, the bytes 0x80
        // to 0x87 and 0xA0 to 0xFF are represented as the bytes 0x00 to
        // 0x07
        // (represented by bytes 0x01 to 0x08) and 0x20 to 0x7F, with a
        // prefix of 1 (see below).
        if (jeb >= 0x80 && jeb <= 0x87) {
            retVal.byteacter = (byte) (jeb - 0x80);
            retVal.prefix = 1;
            return retVal;

        }
        if (jeb >= 0xa0 && jeb <= 0xff) {
            retVal.byteacter = (byte) (jeb - 0x80);
            retVal.prefix = 1;
            return retVal;
        }

        // The byteacters 0x08 to 0x1F are represented
        // as the byteacters 0x28 to 0x3F with a prefix of 2 (see below).
        if (jeb >= 0x08 && jeb <= 0x1f) {
            retVal.byteacter = (byte) (jeb + 0x20);
            retVal.prefix = 2;
            return retVal;
        }

        // The
        // byteacters 0x88 to 0x9F are represented as the byteacters 0x48 to
        // 0x5F with a prefix of 2 (see below).

        if (jeb >= 0x88 && jeb <= 0x9f) {
            retVal.byteacter = (byte) (jeb - 0x40);
            retVal.prefix = 2;
            return retVal;
        }

        // In other words, bytes 0x20 to 0x7F are encoded into the same code
        // positions in the 7-bit byteacter set.
        //
        if (jeb >= 0x20 && jeb <= 0x7f) {
            retVal.byteacter = (byte) (jeb);
            retVal.prefix = 0;
            return retVal;
        }

        return retVal;
    }


    public List<Byte> convertThreeBytes(ByteBuffer bb, int index) {
        List<Byte> retVal = new ArrayList<Byte>();
        int j = 1;
        int prefix = 0;
        for (int i = index; (i < index + 3) && (i < bb.capacity()); i++) {
            int inVal = (bb.get(i) & 0xff);
            SMSbyteacter outVal = translate(inVal);
            retVal.add(outVal.byteacter);
            prefix += outVal.prefix * Math.pow(10, (3 - j));
            j++;
        }
        byte escape = map.get(prefix);
        retVal.add(0, (byte) escape);
        // retVal[0] = (byte) escape;
        return retVal;
    }

    public byte[] messageToBuffer(String inbuf) {
        byte[] inValues = inbuf.getBytes();
        return messageToBuffer(inValues);
    }

    public byte[] turnToBuffer(SMSbyteacter[] outbuf) {

        return null;
    }

    public byte[] getAsci(String unicode) {
        return unicode.getBytes();
    }

    public byte[] messageToBuffer(byte[] inValues) {
        int length = inValues.length;
        List<Byte> newbuffer = new ArrayList<Byte>();
        int j = 0;
        int outIndex = 0;
        int inIndex = 0;

        ByteBuffer bb = ByteBuffer.wrap(inValues);

        for (inIndex = 0; inIndex < length; inIndex++) {
            int jeb = (bb.get(inIndex) & 0xff);
            SMSbyteacter temp = translate(jeb);
            if (temp.prefix != 0) {
                List<Byte> out = convertThreeBytes(bb, inIndex);
                if (out == null)
                    continue;
                int k = 0;

                for (j = 0; (j < 4) && (j < out.size()); j++) {
                    newbuffer.add(out.get(j));
                    // retVal[outIndex++] = out[j];
                }
                inIndex = inIndex + 2;
            } else {
                newbuffer.add(temp.byteacter);
                // retVal[outIndex++] = temp.byteacter;
            }
        }
        byte[] retVal = new byte[newbuffer.size()];
        for (int i = 0; i < newbuffer.size(); i++)
            retVal[i] = newbuffer.get(i);

        return retVal;
    }

    byte byteDecode(int jeb, int prefix) {
        byte retVal = -1;

        if (prefix == 0) {
            // 0x00 to 0x07 are represented as 0x01 to 0x08
            if (jeb >= 1 && jeb <= 0x8) {
                retVal = (byte) (jeb - 1);
                return retVal;
            }

            // bytes 0x20 to 0x7F are encoded into the same code positions in
            // the 7-bit character set
            if (jeb >= 0x20 && jeb <= 0x7f) {
                retVal = (byte) jeb;
                return retVal;
            }
        }

        if (prefix == 1) {
            /*
			 * Of these, the bytes 0x80 to 0x87 and 0xA0 to 0xFF are represented
			 * as the bytes 0x00 to 0x07 (represented by characters 0x01 to
			 * 0x08) and 0x20 to 0x7F, with a prefix of 1 (see below)
			 */
            if (jeb >= 0x0 && jeb <= 0x7) {
                retVal = (byte) (jeb + 0x80);
                return retVal;
            }

            if (jeb >= 0x20 && jeb <= 0x7f) {
                retVal = (byte) (jeb + 0x80);
                return retVal;
            }
        }

        if (prefix == 2) {
			/*
			 * The characters 0x08 to 0x1F are represented as the characters
			 * 0x28 to 0x3F with a prefix of 2
			 */
            if (jeb >= 0x28 && jeb <= 0x3f) {
                retVal = (byte) (jeb - 0x20);
                return retVal;
            }

			/*
			 * The characters 0x88 to 0x9F are represented as the characters
			 * 0x48 to 0x5F with a prefix of 2 (see below).
			 */

            if (jeb >= 0x48 && jeb <= 0x5f) {
                retVal = (byte) (jeb + 0x40);
                return retVal;
            }

        }

        return retVal;
    }

    public List<Byte> decodeThreeBytes(byte[] bb, int index, int esc) {
        List<Byte> retVal = new ArrayList<Byte>();
        int prefix = 0;
        int mask = 0xf00;
        int shift = 8;
        index++;

        for (int i = index; (i < index + 3) && (i < bb.length); i++) {
            prefix = (esc & mask);
            mask = mask >> 4;
            prefix = (prefix >> shift);
            shift = shift - 4;
            int inVal = (bb[i] & 0xff);
            byte outVal = byteDecode(inVal, prefix);
            retVal.add(outVal);
        }

        return retVal;
    }

    public byte[] bufferToMessage(byte[] inbuff) {
        List<Byte> newMessage = new ArrayList<Byte>();
        // int outIndex=0;
        int inIndex = 0;

        while (inIndex < inbuff.length) {
            Byte jeb = (byte) (inbuff[inIndex] & 0x0ff);
            Integer esc = decodeMap.get(jeb);
            if (esc != null) {
                List<Byte> out = decodeThreeBytes(inbuff, inIndex, esc.intValue());
                for (int j = 0; (j < 3) && (j < out.size()); j++) {
                    newMessage.add(out.get(j));
                }
                inIndex = inIndex += 4;
            } else {

                newMessage.add(byteDecode(jeb, 0));
                inIndex++;
            }
        }

        byte[] retVal = new byte[newMessage.size()];
        for (int i = 0; i < retVal.length; i++)
            retVal[i] = newMessage.get(i);
        return retVal;
    }

    boolean buffCompare(byte[] buff1, byte[] buff2) {
        if (buff1.length != buff2.length) return false;
        for (int i = 0; i < buff1.length; i++) {
            if (buff1[i] != buff2[i]) {
                System.out.println("Buffer compare fail at:" + i);
                return false;
            }
        }

        return true;
    }

    public void unitTest() {
        RandomMessages rm = new RandomMessages();

        loadMap();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < 10; i++) {
                byte[] mad = rm.messageBankRandom()[i];
                if (mad == null) continue;
                //System.out.println(HexDump.dumpHexString(mad));
                byte outbuf[] = messageToBuffer(mad);
                //System.out.println(HexDump.dumpHexString(outbuf));
                //System.out.println("Done, this is what is looks like!");
                byte mybuff[] = bufferToMessage(outbuf);
                //System.out.println(HexDump.dumpHexString(mybuff));
                if (buffCompare(mad, mybuff))
                    System.out.println("Success " + j + " " + i + "  " + mybuff.length);
                else {
                    System.out.println("Fail:" + j + " " + i);
                    break;
                }
            }
    }



    private class SMSbyteacter {
        public byte byteacter;
        public byte prefix;
    }
}
