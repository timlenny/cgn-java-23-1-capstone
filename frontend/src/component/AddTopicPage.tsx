// AddTopicPage.js
import React, {useState} from 'react';
import '../style/AddTopicPage.css';
import CheckIcon from '@mui/icons-material/Check';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';

export default function AddTopicPage() {

    const [selectedSize, setSelectedSize] = useState("S");

    const handleSizeButtonClick = (size: string) => {
        setSelectedSize(size);
    };

    return (
        <div className="addTopicPage">
            <p className="label">Parent Topic</p>
            <input
                type="text"
                defaultValue={"HOME"}
                className="inputField"
            />
            <p className="helperText">A::B to select subtopics</p>
            <p className="label">Name</p>
            <input
                type="text"
                placeholder="New Topic"
                className="inputField"
            />
            <p className="label">Size</p>
            <div className="sizeButtons">
                <button
                    className={`sizeButton${selectedSize === 'S' ? ' selectedSize' : ''}`}
                    onClick={() => handleSizeButtonClick('S')}
                >
                    S
                </button>
                <button
                    className={`sizeButton${selectedSize === 'M' ? ' selectedSize' : ''}`}
                    onClick={() => handleSizeButtonClick('M')}
                >
                    M
                </button>
                <button
                    className={`sizeButton${selectedSize === 'XL' ? ' selectedSize' : ''}`}
                    onClick={() => handleSizeButtonClick('XL')}
                >
                    XL
                </button>
            </div>
            <div className="actionButtons">
                <button className="backButton">
                    <ChevronLeftIcon onClick={() => {
                        window.location.assign("/");
                    }} sx={{fontSize: 35}}/>
                </button>
                <button className="confirmButton">
                    <CheckIcon sx={{fontSize: 35}}/>
                </button>
            </div>
        </div>
    );
}
