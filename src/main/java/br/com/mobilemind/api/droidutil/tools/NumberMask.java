package br.com.mobilemind.api.droidutil.tools;

public class NumberMask {

    public static String apply(String text, String pattern){

        if(text == null || pattern == null)
            return  null;

        String numberPattern = "[0-9]+";

        text = AppUtil.filterNumber(text);
        String newText = "";
        int j = 0;

        for(int i = 0; i < pattern.length(); i++){
            Character m = pattern.charAt(i);

            if(j >= text.length()){
                newText += m.toString();
                continue;
            }

            Character c = text.charAt(j);

            if(c.toString().matches(numberPattern)){
                if(m.toString().matches(numberPattern)){
                    newText += c.toString();
                    j++;
                }else{
                    newText += m.toString();
                }
            }
        }

        return newText;
    }

    public static String applyReverse(String text, String pattern){

        if(text == null || pattern == null)
            return  null;

        String numberPattern = "[0-9]+";

        text = new StringBuilder(text).reverse().toString();


        String newText = "";
        int j = 0;

        for(int i = pattern.length()-1; i >= 0; i--  ){
            Character m = pattern.charAt(i);

            if(j >= text.length()){
                newText += m.toString();
                continue;
            }

            Character c = text.charAt(j);

            if(c.toString().matches(numberPattern)){
                if(m.toString().matches(numberPattern)){
                    newText += c.toString();
                    j++;
                }else{
                    newText += m.toString();
                }
            }
        }

        return new StringBuilder(newText).reverse().toString();
    }

    public static  String unmask(String text){
        if(text == null) return  null;

        return  AppUtil.filterNumber(text);
    }

    public static  String documento(String text){

        if(text == null) return  null;

        text =  AppUtil.filterNumber(text);

        if(text.length() == 11) // cpf
            return apply(text, "999.999.999-99");

        if(text.length() == 14) // cnpj
            return apply(text, "99.999.999/9999-99");

        return null;
    }

    public static  String cep(String text){

        if(text == null) return  null;

        text =  AppUtil.filterNumber(text);

        if(text.length() == 8) // cep
            return apply(text, "99999-999");

        return null;
    }

    public static  String telefone(String text){

        if(text == null) return  null;

        text =  AppUtil.filterNumber(text);

        if(text.length() == 9)
            return apply(text, "999.999.999");

        if(text.length() == 8)
            return apply(text, "9999.9999");

	    if(text.length() == 10)
            return apply(text, "(99) 9999.9999");

	    if(text.length() == 11)
            return apply(text, "(99) 9 9999.9999");

        return null;
    }

}
