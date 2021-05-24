/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JogosLotoGestorDeSalas;

import java.util.HashMap;

/**
 *Classe que cria uma caixa de dialogo em Interface gráfica os jogadores  vencedores e as suas devidas recompensas
 * @author William Salvaterra e Rui Oliveira
 *
 */
public class ModalGameScores extends javax.swing.JDialog {

   private final String[][] vencedoresEmArray;
/**
 *Esta classe cria uma Interface gráfica de um gestor de Salas .
     * @param parent o diálogo do proprietário a partir do qual o diálogo é mostrado ou nulo se este diálogo não tiver dono
     * @param modal especifica se a caixa de diálogo bloqueia a entrada do usuário em outras janelas de nível superior quando mostrada. 
     * @param vencedores recebe a Coleção de Vencedores
 */
    public ModalGameScores(java.awt.Frame parent, boolean modal, HashMap<Integer,Double> vencedores) {
        super(parent, modal);
        
           
        vencedoresEmArray = new String[vencedores.size()][2];

        int x = 0;
        for(int valor : vencedores.keySet()){
            vencedoresEmArray[x][0] = Integer.toString(valor);
            vencedoresEmArray[x][1] = Double.toString(vencedores.get(valor));
            x++;
        }       
        
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Vencedores");
        setBackground(new java.awt.Color(0, 204, 0));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jTable1.setBackground(new java.awt.Color(102, 204, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            vencedoresEmArray,
            new String [] {
                "Jogador ID", "Valor Premiado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
