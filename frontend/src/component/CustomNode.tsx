import React, {FC} from 'react';
import {Handle, Position} from 'reactflow';
import '../style/home/customeNodeAnimation.css';
import DefaultNode from './DefaultNode';

type CustomLabelNodeProps = {
    id: string;
    data: {
        label: string;
    };
};

const CustomLabelNode: FC<CustomLabelNodeProps> = ({id, data}) => {
    const {label} = data;
    const isHome = label === 'HOME';

    return (
        <>
            {isHome ? (
                <>
                    <div className="circle">
                        <div className="blob blob1"></div>
                        <div className="blob blob2"></div>
                        <div className="blob blob3"></div>
                    </div>
                    <div className="circle-text">{label}</div>
                </>
            ) : (
                <DefaultNode id={id} data={data}/>
            )}
            <Handle type="target" position={Position.Top}/>
            <Handle type="source" position={Position.Bottom}/>
        </>
    );
};

export default React.memo(CustomLabelNode);
