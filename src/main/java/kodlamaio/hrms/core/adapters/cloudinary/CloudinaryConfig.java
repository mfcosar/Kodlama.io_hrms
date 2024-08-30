package kodlamaio.hrms.core.adapters.cloudinary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "*******",
            "api_key", "**************",
            "api_secret", "**************"));
        //cloudainary free üyelikte verilen hesap bilgileri yazılacak
    }
    //3 saat 20 dakikadır bilg. başındayım saat: 01.16 ve cloudaniary'e image gönderildi, DB'e kaydedildi.....30.8.2024
}

