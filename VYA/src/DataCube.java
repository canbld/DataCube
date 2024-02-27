public class DataCube<T> {
    private int dimensions;
    private int[] sizes;
    private T[][] dimensionValues;
    private Object[][][] cube;

    public DataCube(T[]... dimensionValues) {
        this.dimensions = dimensionValues.length;
        this.sizes = new int[dimensions];
        this.dimensionValues = dimensionValues;
        this.cube = new Object[1][1][1];

        for (int i = 0; i < dimensions; i++) {
            for (T dimensionValue : dimensionValues[i]) {
                addDimensionValue(i, dimensionValue);
            }
        }
    }

    public void addDimensionValue(int dimensionIndex, T dimensionValue) {
        if (dimensionIndex < 0 || dimensionIndex >= dimensions) {
            throw new IllegalArgumentException("Geçersiz boyut endeksi");
        }

        int newDimensionSize = sizes[dimensionIndex] + 1;
        Object[][][] newCube = new Object[newDimensionSize][sizes.length][sizes[dimensionIndex]];

        if (dimensions > 1) {
            for (int i = 0; i < newDimensionSize; i++) {
                for (int j = 0; j < sizes.length; j++) {
                    newCube[i][j] = new Object[sizes[dimensionIndex]];
                    if (i < sizes[dimensionIndex]) {
                        int length = Math.min(cube[i][j].length, sizes[dimensionIndex]);
                        System.arraycopy(cube[i][j], 0, newCube[i][j], 0, length);
                    }
                }
            }
        }

        sizes[dimensionIndex] = newDimensionSize;
        cube = newCube;
        dimensionValues[dimensionIndex][newDimensionSize - 1] = dimensionValue;
    }

    public void setValue(T dimension1, T dimension2, T dimension3, Object value) {
    int index1 = getIndex(dimension1, 0);
    int index2 = getIndex(dimension2, 1);
    int index3 = getIndex(dimension3, 2);

    if (index1 == -1 || index2 == -1 || index3 == -1) {
        throw new IllegalArgumentException("Bir boyut küp içinde mevcut değil.");
    } else if (index1 >= sizes[0] || index2 >= sizes[1] || index3 >= sizes[2]) {
        throw new ArrayIndexOutOfBoundsException("Dizi sınırları dışında bir endeksleme yapılmış.");
    } else {
        cube[index1][index2][index3] = value;
    }
}




    private int getIndex(T dimensionValue, int dimensionIndex) {
    for (int i = 0; i < dimensionValues[dimensionIndex].length; i++) {
        if (dimensionValue.equals(dimensionValues[dimensionIndex][i])) {
            return i;
        }
    }
    return -1;
}

    public Object getValue(T dimension1, T dimension2, T dimension3) {
        int index1 = getIndex(dimension1, 0);
        int index2 = getIndex(dimension2, 1);
        int index3 = getIndex(dimension3, 2);

        if (index1 != -1 && index2 != -1 && index3 != -1) {
            return cube[index1][index2][index3];
        }
        return null;
    }

    public static void main(String[] args) {
        DataCube<String> dataCube = new DataCube<>(new String[]{"1234567890"}, new String[]{"Veri Yapıları ve Algoritmalar", "Nesneye Yönelik Programlama"}, new String[]{"2022-2023", "2023-2024"});

        // Değer atamak için 
        dataCube.setValue("1234567890", "Veri Yapıları ve Algoritmalar", "2022-2023", 40);
        dataCube.setValue("1234567890", "Veri Yapıları ve Algoritmalar", "2022-2023", 90);
        dataCube.setValue("1234567890", "Nesneye Yönelik Programlama", "2022-2023", 80);

        // Değer okumak için 
        Object value = dataCube.getValue("1234567890", "Veri Yapıları ve Algoritmalar", "2022-2023");
        System.out.println("Value: " + value);
    }
}
