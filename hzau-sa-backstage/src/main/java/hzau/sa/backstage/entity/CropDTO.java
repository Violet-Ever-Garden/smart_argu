package hzau.sa.backstage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作物数据传输类
 * @author haokai
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CropDTO {
    private String cropName;
    private MultipartFile picture;
}
