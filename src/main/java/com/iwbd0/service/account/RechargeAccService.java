package com.iwbd0.service.account;

import com.iwbd0.entity.repo.AccountRepo;
import com.iwbd0.model.entity.Account;
import com.iwbd0.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class RechargeAccService {

    @Autowired
    AccountRepo accRepo;

    public BaseResponse rechargeAccount(String codiceconto, Double importo) {
        BaseResponse response = new BaseResponse();
        // controllo se esiste conto
        Optional<Account>  acc = Optional.ofNullable(accRepo.findByCodiceconto(codiceconto));

        if(acc.isEmpty()) {
            response.setError(true);
            response.setCodiceEsito("ERKO-02");
            response.setErrDsc("Error on finding acc");
            return response;
        }
        Boolean debitWay = (acc.get().getDebito() > 0) ? true : false;

        if(debitWay) {
            // controllo quanto ricaricare effettivo e se copre debito
            if(acc.get().getDebito() > importo) {
                // aggiorno il debito
                acc.get().setDebito(acc.get().getDebito() - importo);
            } else {
                // aggiorno il debito e il saldo
                acc.get().setDebito(0.0);
                acc.get().setSaldoattuale(acc.get().getSaldoattuale() + (importo - acc.get().getDebito()));
            }
        } else {
            // aggiorno il saldo
            acc.get().setSaldoattuale(acc.get().getSaldoattuale() + importo);
        }

        // updato account
        accRepo.save(acc.get());
        return response;
    }
}
