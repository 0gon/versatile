import Header from '@/components/Header';
import Footer from '@/components/Footer';

const BasicLayout = ({children}) => {
    return (
        <>
            <Header></Header>
            {children}
            <Footer></Footer>
        </>
    )
}

export default BasicLayout;