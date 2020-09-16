package pl.goreit.blog.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.goreit.api.generated.account.CreateAccountRequest;
import pl.goreit.blog.domain.model.Account;

@Component
public class CreateAccountRequestToAccountConverter implements Converter<CreateAccountRequest, Account> {

    @Autowired
    private AddressViewToAddressConverter addressViewToAddressConverter;

    @Override
    public Account convert(CreateAccountRequest request) {
        return new Account(request.getUserId(),request.getFirstName(),request.getLastName(), addressViewToAddressConverter.convert(request.getAddress()));
    }
}
