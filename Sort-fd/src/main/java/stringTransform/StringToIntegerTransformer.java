package stringTransform;

import exception.DataTransformErrorException;

public class StringToIntegerTransformer implements Transformer<String, Integer> {

    @Override
    public Integer transform(String data) throws DataTransformErrorException {
        int value;
        try {
            value = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            throw new DataTransformErrorException("This String can't be transformed to Integer" +
                    e.getMessage());
        }
        return value;
    }
}
