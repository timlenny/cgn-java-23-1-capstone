import React, {FC} from 'react';
import {Handle, Position} from 'reactflow';
import '../style/home/reactFlow.css'

type DefaultNodeProps = {
    id: string;
    data: {
        label: string;
    };
};

const DefaultNode: FC<DefaultNodeProps> = ({data}) => {
    return (
        <>
            <div className="default-node">
                {data.label}
            </div>
            <Handle type="target" position={Position.Top}/>
            <Handle type="source" position={Position.Bottom}/>
        </>
    );
};

export default DefaultNode;
