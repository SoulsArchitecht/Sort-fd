package stringTransform;

public class StringToStringTransformer implements Transformer <String, String> {

    @Override
    public String transform(String data) {
        return data;
    }
}
