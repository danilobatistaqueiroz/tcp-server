/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.labs.prova;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.labs.prova.exceptions.ClientInputException;
import com.labs.prova.infra.ProtocolModule;
import com.labs.prova.interfaces.Protocol;
import com.labs.prova.validations.InputQueryResultEnum;

class PayloadCharsProtocolTest {
    
    BufferedReader inServer;
    ByteArrayOutputStream baos;
    PrintWriter outClient;
    Socket clientSocket;
    
    private void mockObjects() throws IOException {
        inServer = mock(BufferedReader.class);
        baos = new ByteArrayOutputStream();
        outClient = new PrintWriter(baos);
        clientSocket = mock(Socket.class);
        when(clientSocket.getOutputStream()).thenReturn(System.out);        
    }
    private void closeObjects() throws IOException {
        inServer.close();
        clientSocket.close();
        outClient.flush();
    }
    
    @Test 
    @DisplayName("read client input using criteria mib")
    void readClientInputMib() throws IOException, ClientInputException {
        mockObjects();
        int[] i = stringToIntArray("3:mib");
        when(inServer.read()).thenReturn(i[0],i[1],i[2],i[3],i[4]);
        InputQueryResultEnum result = injectProtocol(inServer, outClient).readClientInput();
        closeObjects();
        assertTrue(baos.toString().indexOf("Homens de Preto III")>0);
        assertEquals(InputQueryResultEnum.END_OF_QUERY,result);
    }
    
    @Test 
    @DisplayName("read client input using criteria mib and spider man")
    void readClientInputMibAndSpiderMan() throws IOException, ClientInputException {
        mockObjects();
        int[] i = stringToIntArray("3:mib10:spider man");
        when(inServer.read()).thenReturn(i[0],i[1],i[2],i[3],i[4],i[5],i[6],i[7],i[8],i[9],i[10],i[11],i[12],i[13],i[14],i[15],i[16],i[17]);
        InputQueryResultEnum result = injectProtocol(inServer, outClient).readClientInput();
        closeObjects();
        assertTrue(baos.toString().indexOf("Homens de Preto III")>0);
        assertEquals(InputQueryResultEnum.END_OF_QUERY,result);
    }
    
    @Test
    @DisplayName("read client input without a separator")
    void readClientInputWithoutSeparator() throws IOException, ClientInputException {
        mockObjects();
        int[] i = stringToIntArray("3mib");
        when(inServer.read()).thenReturn(i[0],i[1],i[2],i[3]);
        try {
            injectProtocol(inServer, outClient).readClientInput();
        } catch (ClientInputException ex ) {
            assertEquals("Invalid query length!", ex.getMessage());
        } finally {
            closeObjects();
        }
    }
    
    @Test 
    @DisplayName("read client input using criteria as null")
    void readClientInputNull() throws IOException, ClientInputException {
        mockObjects();
        when(inServer.read()).thenReturn(-1);
        InputQueryResultEnum result = injectProtocol(inServer, outClient).readClientInput();
        clientSocket.close();
        assertEquals(InputQueryResultEnum.WARNING,result);
    }
    
    @Test 
    @DisplayName("read client input using criteria an empty")
    void readClientInputEmpty() throws IOException, ClientInputException {
        mockObjects();
        when(inServer.read()).thenReturn((int)' ');
        try {
            injectProtocol(inServer, outClient).readClientInput();
        } catch (ClientInputException ex ) {
            assertEquals("Invalid query length!", ex.getMessage());
        } finally {
            closeObjects();
        }
    }
    
    @Test 
    @DisplayName("read client inputs using criteria iron man and joker")
    void readClientInputsIronManAndJoke() throws IOException, ClientInputException {
        mockObjects();
        int[] i = stringToIntArray("8:iron man5:joker");
        when(inServer.read()).thenReturn(i[0],i[1],i[2],i[3],i[4],i[5],i[6],i[7],i[8],i[9],i[10],i[11],i[12],i[13],i[14],i[15],i[16]);
        try {
            injectProtocol(inServer, outClient).readClientInputs();
        } catch (ClientInputException ex ) {
            assertEquals("Invalid query length!", ex.getMessage());
        } finally {
            closeObjects();
        }
    }
    
    @Test 
    @DisplayName("read client inputs using criteria iron man and joker with wrong length")
    void readClientInputsWithWrongLength() throws IOException, ClientInputException {
        mockObjects();
        int[] i = stringToIntArray("2:iron man3:joker");
        when(inServer.read()).thenReturn(i[0],i[1],i[2],i[3],i[4],i[5],i[6],i[7],i[8],i[9],i[10],i[11],i[12],i[13],i[14],i[15],i[16]);
        try {
            injectProtocol(inServer, outClient).readClientInput();
        } catch (ClientInputException ex ) {
            assertEquals("Invalid query length!", ex.getMessage());
        } finally {
            closeObjects();
        }
    }
    
    @Test 
    @DisplayName("read client inputs using criteria iron without length")
    void readClientInputsWithoutLength() throws IOException, ClientInputException {
        mockObjects();
        int[] i = stringToIntArray("iron");
        when(inServer.read()).thenReturn(i[0],i[1],i[2],i[3]);
        try {
            injectProtocol(inServer, outClient).readClientInput();
        } catch (ClientInputException ex ) {
            assertEquals("Invalid query length!", ex.getMessage());
        } finally {
            closeObjects();
        }
    }
    
    @Test
    @DisplayName("print output lines")
    void printOutputLines() throws IOException {
        String output = "50:MIB 1997\nHomens de Preto III\n";
        mockObjects();
        PayloadCharsProtocol protocol = new PayloadCharsProtocol(inServer, outClient);
        String[] results = protocol.printOutputLines(output);
        closeObjects();
        assertEquals("50:MIB 1997",results[0]);
        assertEquals("Homens de Preto III",results[1]);
    }
    
    private int[] stringToIntArray(String query) {
        char[] ca = query.toCharArray();
        int[] ia = new int[ca.length];
        for(int i = 0; i < ca.length; i++) {
            ia[i] = ca[i];
        }
        return ia;
    }
    
    Protocol injectProtocol(BufferedReader inServer, PrintWriter outClient) {
        Injector injector = Guice.createInjector(new ProtocolModule(inServer, outClient));
        return injector.getInstance(Protocol.class);
    }
}