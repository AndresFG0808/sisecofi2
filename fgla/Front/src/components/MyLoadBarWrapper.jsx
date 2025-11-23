import React, { Component } from 'react';
import { ThreeCircles } from 'react-loader-spinner';

class MyLoadBarWrapper extends Component {

    constructor(props) {
        super(props);
        console.log(props);
        this.state = {
            isVisible: props.visible
        }
    }

    handleOpenLoadBar() {
        this.setState({ isVisible: true });
    }

    handleCloseLoadBar() {
        this.setState({ isVisible: false });
    }

    render() {
        return (
            <div className="Div-load-bar text-center" hidden={!this.state.isVisible}>
                <ThreeCircles color="#BC955C" height={220} width={220} visible={this.state.isVisible} />
            </div>
        );
    }

}

export default MyLoadBarWrapper