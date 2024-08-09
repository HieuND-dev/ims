import loading from "../assets/img/loading.gif";
import loading_2 from "../assets/img/loading_2.gif"
import "../assets/css/loadingImg.css"


const LoadingImg = () => {
    return (
        <div className="loading-img-css">
            <img src={loading_2} width={"300px"} alt="Loading" />
        </div>
    )
}

export default LoadingImg