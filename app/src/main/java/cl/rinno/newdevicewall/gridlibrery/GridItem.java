package cl.rinno.newdevicewall.gridlibrery;


import java.io.Serializable;

import cl.rinno.newdevicewall.models.Producto;


public abstract class GridItem implements Serializable, Cloneable {


    private int rowSpec = 1;
    private int columnSpec = 1;
    private int row;
    private int column;
    private int width;
    private int height;

    private Producto producto;

    public GridItem() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getRowSpec() {
        return rowSpec;
    }

    public void setRowSpec(int rowSpec) {
        this.rowSpec = rowSpec;
    }

    public int getColumnSpec() {
        return columnSpec;
    }

    public void setColumnSpec(int columnSpec) {
        this.columnSpec = columnSpec;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "GridItem{" +
                "rowSpec=" + rowSpec +
                ", columnSpec=" + columnSpec +
                ", row=" + row +
                ", column=" + column +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
