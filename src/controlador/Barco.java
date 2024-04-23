package controlador;

public class Barco {
    public int tipo;
    public boolean vertical = true;

    private int vida;

    public Barco(int tipo, boolean vertical) {
        this.tipo = tipo;
        this.vertical = vertical;
        vida = tipo;

        /*VBox vbox = new VBox();
        for (int i = 0; i < tipo; i++) {
            Rectangle square = new Rectangle(30, 30);
            square.setFill(null);
            square.setStroke(Color.BLACK);
            vbox.getChildren().add(square);
        }

        getChildren().add(vbox);*/
    }

    public void impacto() {
        vida--;
    }

    public boolean estaVivo() {
        return vida > 0;
    }
}
