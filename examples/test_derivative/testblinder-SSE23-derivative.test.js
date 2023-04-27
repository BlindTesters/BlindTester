const mathjs = require('mathjs');

describe('blindtester-SSE23-derivative', () => {
    test('mathjs.derivative("x^2","x") should returns {"mathjs":"OperatorNode","op":"*","fn":"multiply","args":[{"mathjs":"ConstantNode","value":2.0},{"mathjs":"SymbolNode","name":"x"}],"implicit":false,"isPercentage":false}', () => {
        expect(JSON.parse(JSON.stringify(mathjs.derivative("x^2","x")))).toMatchObject({"mathjs":"OperatorNode","op":"*","fn":"multiply","args":[{"mathjs":"ConstantNode","value":2.0},{"mathjs":"SymbolNode","name":"x"}],"implicit":false,"isPercentage":false});
    });
});