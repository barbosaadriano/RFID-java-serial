/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testerfid;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author drink
 */
public class Tela extends JFrame implements MeuListener{
    
    private JTable tbl;
    private HashMap<String,Object> map;
    Leitura lt;
    public Tela() throws HeadlessException {
        super("Leitura");
        setLayout(new BorderLayout());
        JScrollPane pn = new JScrollPane();
        this.add(pn,BorderLayout.CENTER);

        tbl = new JTable(new Object[][]{}, new Object[]{"TAG","Contagem"});
        pn.setViewportView(tbl);
        map = new HashMap<>();
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        lt = new Leitura(this);
        lt.run();
    }

    @Override
    public void performEvent(String tag) {
        Object get = map.get(tag);
        if(get == null) {
            map.put(tag, tag);
            updateTabela();
        }
    }

    private void updateTabela() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"TAG","Item"}, 0);
        Iterator<String> it = map.keySet().iterator();
        int cont = 0;
        while(it.hasNext()) {
            cont++;
            String next = it.next();
            model.addRow(new Object[]{next,cont});
        }
        tbl.setModel(model);
    }

  
    
    
    
}
