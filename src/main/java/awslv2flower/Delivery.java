package awslv2flower;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import awslv2flower.external.Review;

import java.util.List;

@Entity
@Table(name="Delivery_table")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String status;

    @PostUpdate
    public void onPostUpdate(){

        if(this.getStatus().equals("DeliveryStart")) {

            Shipped shipped = new Shipped();
            BeanUtils.copyProperties(this, shipped);
            shipped.publishAfterCommit();

            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.



        //    awslv2flower.external.Review reviewRequest = new awslv2flower.external.Review();
        

        //    reviewRequest.setOrderId(this.getOrderId());
        
        //    reviewRequest.setStatus("reviewRequest");

        //    DeliveryApplication.applicationContext.getBean(awslv2flower.external.ReviewService.class).reviewRequest(reviewRequest);



        }else if (this.getStatus().equals("DeliveryCancelled")){

            ShipCancelled shipCancelled = new ShipCancelled();
            BeanUtils.copyProperties(this, shipCancelled);
            shipCancelled.publishAfterCommit();

        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
