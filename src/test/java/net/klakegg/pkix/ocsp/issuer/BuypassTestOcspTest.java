package net.klakegg.pkix.ocsp.issuer;

import net.klakegg.pkix.ocsp.*;
import net.klakegg.pkix.ocsp.util.CertificateHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.net.URI;
import java.security.cert.X509Certificate;

/**
 * @author erlend
 */
public class BuypassTestOcspTest {

    private X509Certificate subject =
            CertificateHelper.parse(getClass().getResourceAsStream("/buypass-test/certificate-valid-01.cer"));

    private X509Certificate issuer =
            CertificateHelper.parse(getClass().getResourceAsStream("/buypass-test/issuer.cer"));

    @Test
    public void simple() throws OcspException {
        OcspClient ocspClient = OcspClient.builder()
                .build();

        CertificateResult response = ocspClient.verify(subject, issuer);

        Assert.assertEquals(response.getStatus(), CertificateStatus.GOOD);
        Assert.assertEquals(response.getIssuer(), CertificateIssuer.generate(issuer));
        Assert.assertEquals(response.getUri(), URI.create("http://ocsp.test4.buypass.no/ocsp/BPClass3T4CA3"));
        Assert.assertEquals(response.getSerialNumber(), new BigInteger("1554383620002867819762479"));
        Assert.assertNotNull(response.getThisUpdate());
        Assert.assertNotNull(response.getNextUpdate());
    }
}
