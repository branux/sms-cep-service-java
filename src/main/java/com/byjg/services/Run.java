package com.byjg.services;

import com.byjg.services.cep.CEPService;
import com.byjg.services.sms.SMSService;

import static java.lang.System.*;

public class Run {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            out.println("Você precisa passar: servico usuario senha");
            exit(1);
        }

        if (args[0].equals("cep")) {
            cep(args[1], args[2]);
        } else {
            if (args[0].equals("sms")) {
                sms(args[1], args[2]);
            }
        }
    }

    private static void cep(String usuario, String senha) {
        CEPService cepService = new CEPService(usuario, senha);

        try {
            out.println(cepService.obterCEP("Rio Branco", "Rio de Janeiro", "RJ"));
        } catch (Exception ex) {
            err.println(ex.getMessage());
        }
    }

    private static void sms(String usuario, String senha) {
        SMSService smsService = new SMSService(usuario, senha);

        try {
            out.println(smsService.creditos());
        } catch (Exception ex) {
            err.println(ex.getMessage());
        }
    }
}
