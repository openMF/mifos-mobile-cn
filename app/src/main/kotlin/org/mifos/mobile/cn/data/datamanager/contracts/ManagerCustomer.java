package org.mifos.mobile.cn.data.datamanager.contracts;

import org.mifos.mobile.cn.data.models.customer.Command;
import org.mifos.mobile.cn.data.models.customer.Customer;
import org.mifos.mobile.cn.data.models.customer.identification.Identification;
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface ManagerCustomer {



    Observable<Customer> fetchCustomer(String identifier);

    Completable updateCustomer(String customerIdentifier, Customer customer);





    Completable customerCommand(String identifier, Command command);

    Observable<List<Command>> fetchCustomerCommands(String customerIdentifier);

    Observable<List<Identification>> fetchIdentifications(String customerIdentifier);

    Completable createIdentificationCard(String identifier, Identification identification);

    Completable updateIdentificationCard(String customerIdentifier, String identificationNumber,
                                         Identification identification);

    Observable<List<ScanCard>> fetchIdentificationScanCards(String customerIdentifier,
                                                            String identificationNumber);

    Completable uploadIdentificationCardScan(String customerIdentifier, String identificationNumber,
                                             String scanIdentifier, String description,
                                             MultipartBody.Part file);

    Completable deleteIdentificationCardScan(String customerIdentifier, String identificationNumber,
                                             String scanIdentifier);

    Completable deleteIdentificationCard(String customerIdentifier, String identificationnumber);

    Completable uploadCustomerPortrait(String customerIdentifier, MultipartBody.Part file);

    Completable deleteCustomerPortrait(String customerIdentifier);
}
