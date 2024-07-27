package com.example.my_chat.infra;

import com.example.my_chat.infra.exception.ValidacaoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.Set;

public abstract class FuncionalidadesService {
    public static String formatarNomeArquivo(String nomeArquivo) {
        // Transforma tudo em letra minúscula
        nomeArquivo = nomeArquivo.toLowerCase();
        // Remove caracteres especiais
        nomeArquivo = removerCaracteresEspeciais(nomeArquivo);
        // Substitui espaços por _
        nomeArquivo = nomeArquivo.replace(" ", "_");
        return nomeArquivo;
    }

    public static String removerCaracteresEspeciais(String texto) {
        // Remove acentos e outros caracteres especiais
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        return texto;
    }

    public static String extrairNomeArquivo(String linkArquivo) {
        if(linkArquivo == null) return null;

        String[] linkPartido = linkArquivo.split("/");
        String nomeArquivo = linkPartido[linkPartido.length - 1];
        return nomeArquivo;
    }

    public static String gerarNomeArquivoTimestamp(String nomeOriginal) {
        String nomeArquivo = StringUtils.cleanPath(nomeOriginal);
        String nomeBase = nomeArquivo.substring(0, nomeArquivo.lastIndexOf('.'));
        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
        return nomeBase + "_" + System.currentTimeMillis() + extensao;
    }
    public static <T> void validarRecord(T record) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<T>> violations = validator.validate(record);

        if (!violations.isEmpty()) {
            StringBuilder mensagem = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
//                mensagem.append(String.format("""
//                        Violations de validação:
//                         - Propriedade: %s
//                         - Valor inválido: %s
//                         - Erro: %s"
//                        """,
//                        violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage()));
//                mensagem.append(" Atributo: ").append(violation.getPropertyPath());
//                mensagem.append(" - Valor: ").append(violation.getInvalidValue());
//                mensagem.append(" - Message: ").append(violation.getMessage());
                mensagem.append(violation.getMessage());
                mensagem.append("/");
            }
            throw new ValidacaoException(mensagem.toString());
        }
    }


}
