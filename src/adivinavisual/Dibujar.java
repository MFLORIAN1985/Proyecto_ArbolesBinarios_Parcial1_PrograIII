package adivinavisual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

/**
*
* @author Florian
*/

public class Dibujar extends javax.swing.JPanel {

    private ArbolB.Nodo arbol;
    private HashMap positionNodes, subtreeSizes;
    private Dimension empty; 
    private FontMetrics fontMetrics; //Permite conocer la características de una fuente de texto
    private boolean dirty;
    private int parentToChild = 20, childToChild = 30;
    
    public Dibujar(ArbolB.Nodo arbol) {
        
        initComponents();
        
        this.arbol = arbol;
        
        super.repaint();
        
        //HashMap almacena un mapa formado por un par de datos clave-valor
        this.positionNodes = new HashMap();//Permite encontrar la posición de los nodos en un arbol binario
        this.subtreeSizes = new HashMap();//Sirva para formar el esquelo del arbol binario, para saber las dimensiones de lo sub arboles
        this.empty = new Dimension(0,0);
        this.dirty = true;
    }

    private void calculateLocations() {
        positionNodes.clear();
        subtreeSizes.clear();
        
        ArbolB.Nodo root = arbol;
        
        if (root != null) {
            calculateSubtreeSize(root);
            calculatePosition(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0); 
        }
    }
    
    private Dimension calculateSubtreeSize(ArbolB.Nodo node) {
        if (node == null){
            return new Dimension(0,0);
        }
        Dimension ld = calculateSubtreeSize(node.izq);//izquierda
        Dimension rd = calculateSubtreeSize(node.der);//derecha
          
        int height = fontMetrics.getHeight() + parentToChild + Math.max(ld.height, rd.height);
        int weight = ld.width + childToChild + rd.width;

        Dimension dimension = new Dimension(weight, height);
        subtreeSizes.put(node, dimension);

        return dimension;
    }
    
    private void calculatePosition(ArbolB.Nodo node, int left, int right, int top) { 
        if (node == null) {
            return;
        }
        
        Dimension ld = (Dimension) subtreeSizes.get(node.izq);
        if (ld == null) {
            ld = empty;//izquierda
        }  
      
        Dimension rd = (Dimension) subtreeSizes.get(node.der);
        
        if (rd == null) {
            rd = empty;//Derecha
        }
        int center = 0;
        if (right != Integer.MAX_VALUE) {   
            center = right - rd.width - childToChild/2;
        }else {   
            if (left != Integer.MAX_VALUE) {
                center = left + ld.width + childToChild/2;
            }
        } 
        
        int width = fontMetrics.stringWidth(node.info+"");
 
        positionNodes.put(node,new Rectangle(center - width/2 - 3, top, width + 6, fontMetrics.getHeight()));
      
        calculatePosition(node.izq, Integer.MAX_VALUE, center - childToChild/2, top + fontMetrics.getHeight() + parentToChild);
        calculatePosition(node.der, center + childToChild/2, Integer.MAX_VALUE, top + fontMetrics.getHeight() + parentToChild);
        
    }
    //Configuración para la ventana donde se dibuja el arbol binario
    private void drawTree(Graphics2D g, ArbolB.Nodo n, int puntox, int puntoy, int yoffs) {
        if (n == null) {
            return;
        }
        Rectangle rect = (Rectangle) positionNodes.get(n);
        g.draw(rect);
      
        g.drawString(n.info + "", rect.x + 3, rect.y + yoffs);
        g.setFont(new java.awt.Font("Calibri", 1, 12));
        g.setColor(Color.blue);
        if (puntox != Integer.MAX_VALUE)
       
        g.drawLine(puntox, puntoy, (int)(rect.x + rect.width/2), rect.y);
     
        drawTree(g, n.izq, (int)(rect.x + rect.width/2), rect.y + rect.height, yoffs);
        drawTree(g, n.der, (int)(rect.x + rect.width/2), rect.y + rect.height, yoffs);
   }
    
    @Override
   public void paint(Graphics graphics) {
        super.paint(graphics);
        fontMetrics = graphics.getFontMetrics();
        if (dirty) {
            calculateLocations();
            dirty = false;
        }
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(getWidth() / 2, parentToChild);
        drawTree(g2d, this.arbol, Integer.MAX_VALUE, Integer.MAX_VALUE, fontMetrics.getLeading() + fontMetrics.getAscent());
        fontMetrics = null;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        
        //Panel principal color
        setBackground(new java.awt.Color(127,226,255));
        //Tamaño del panel
        setPreferredSize(new java.awt.Dimension(960, 500));
        
        //Panel de Dibujo de árbol
        jPanel1.setBackground(new java.awt.Color(245,248,159));
        jLabel2.setFont(new java.awt.Font("Bradley Hand ITC", 1, 30)); // NOI18N
        jLabel2.setForeground(new Color (241,127,172));
        jLabel2.setText("Dibujo del Árbol");


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        //Panel Derecho
        jPanel2.setBackground(new java.awt.Color(127,226,255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 492, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 399, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Declaración de variables - no modificar
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    
}
