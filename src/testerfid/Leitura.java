package testerfid;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author drink
 */
public class Leitura implements Runnable {

    SerialPort serialPort = null;
    private boolean running = true;
    private String linha = "";
    private MeuListener ln;
    public Leitura(MeuListener l) {
        this.ln = l;
        serialPort = new SerialPort("COM3");
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));

        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                try {
                    linha+= serialPort.readString(1);
                    if (linha.endsWith("#EOL#")) {
                        String tmp = linha.trim();
                        linha="";
                        if(tmp.startsWith("readedTag:")) {
                            ln.performEvent(tmp.substring(10, 21).replaceAll("\\s", ""));
                        }
                    }
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        try {
            System.out.println("Port closed: " + serialPort.closePort());
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    public void close() {
        this.running = false;
    }
   
 

}
